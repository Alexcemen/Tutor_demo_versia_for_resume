package ru.project.tutor.common_ui.shared

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import ru.rustore.sdk.review.RuStoreReviewManagerFactory
import timber.log.Timber
import ru.rustore.sdk.review.model.ReviewInfo as RuReviewInfo

interface AppReviewManager {
    fun request()
    fun start(activity: Activity)
}

class GoogleReviewManager(context: Context) : AppReviewManager {
    private val manager = ReviewManagerFactory.create(context)
    private var reviewInfo: ReviewInfo? = null
    override fun request() {
        if (reviewInfo != null) {
            Timber.e("request:: reviewInfo != null")
            return
        }
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.i("request:: review load success")
                reviewInfo = task.result
            } else {
                @ReviewErrorCode val reviewErrorCode =
                    (task.getException() as ReviewException).errorCode
                Timber.e("request:: reviewErrorCode = $reviewErrorCode")
            }
        }.addOnFailureListener {
            Timber.e("request:: addOnFailureListener = ${it.message}")
        }
    }

    override fun start(activity: Activity) {
        if (reviewInfo == null) {
            Timber.e("start:: reviewInfo == null")
            return
        }
        manager.launchReviewFlow(activity, reviewInfo ?: return).addOnCompleteListener { _ ->
            Timber.e("start:: launchReviewFlow completed")
        }
    }
}

class RustoreReviewManager(context: Context) : AppReviewManager {
    val manager = RuStoreReviewManagerFactory.create(context)
    private var _reviewInfo: RuReviewInfo? = null

    override fun request() {
        if (_reviewInfo != null) {
            Timber.e("request:: reviewInfo != null")
            return
        }
        manager.requestReviewFlow()
            .addOnSuccessListener { reviewInfo ->
                _reviewInfo = reviewInfo
            }
            .addOnFailureListener { throwable ->
                Timber.e("request:: addOnFailureListener = ${throwable.message}")
            }
            .addOnCompletionListener { Timber.e("request:: addOnFailureListener = ${it?.message}") }
    }

    override fun start(activity: Activity) {
        if (_reviewInfo == null) {
            Timber.e("start:: reviewInfo == null")
            return
        }
        manager.launchReviewFlow(_reviewInfo ?: return).addOnSuccessListener { _ ->
            Timber.e("start:: launchReviewFlow completed")
        }
    }
}