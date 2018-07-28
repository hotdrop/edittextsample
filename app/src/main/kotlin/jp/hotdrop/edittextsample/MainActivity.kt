package jp.hotdrop.edittextsample

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import jp.hotdrop.edittextsample.component.ExEditText
import jp.hotdrop.edittextsample.component.toObservable
import jp.hotdrop.edittextsample.type.LoginId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        observeNormalEditText()
        observeEditTextWithInputTextLayout()
        observeEditTextWithInputTextLayout2()
        observeExEditText()
    }

    private fun observeNormalEditText() {

        fun clearErrorMessage() {
            normalEditText.error = null
        }
        fun showErrorMessage(@StringRes resId: Int) {
            normalEditText.error = getString(resId)
        }

        RxTextView.textChangeEvents(normalEditText).
                skip(1).
                map { LoginId.validation(it.text().toString()) }.
                distinctUntilChanged().
                subscribeBy(
                        onNext = {
                            when (it) {
                                is LoginId.Companion.ValidateResult.ErrorEmpty -> showErrorMessage(R.string.error_message_is_empty)
                                is LoginId.Companion.ValidateResult.ErrorInputInvalidChar -> showErrorMessage(R.string.error_message_invalid_char)
                                is LoginId.Companion.ValidateResult.Correct -> clearErrorMessage()
                            }
                        }
                ).addTo(compositeDisposable)
    }

    private fun observeEditTextWithInputTextLayout() {

        fun clearErrorMessage() {
            textInputLayout1.error = null
        }

        fun showErrorMessage(@StringRes resId: Int) {
            textInputLayout1.error = getString(resId)
        }

        RxTextView.textChangeEvents(editText1).
                skip(1).
                map { LoginId.validation(it.text().toString()) }.
                distinctUntilChanged().
                subscribeBy(
                        onNext = {
                            when (it) {
                                is LoginId.Companion.ValidateResult.ErrorEmpty -> showErrorMessage(R.string.error_message_is_empty)
                                is LoginId.Companion.ValidateResult.ErrorInputInvalidChar -> showErrorMessage(R.string.error_message_invalid_char)
                                is LoginId.Companion.ValidateResult.Correct -> clearErrorMessage()
                            }
                        }
                ).addTo(compositeDisposable)
    }

    private fun observeEditTextWithInputTextLayout2() {

        fun clearErrorMessage() {
            textInputLayout2.error = null
            editText2.error = null
        }

        fun showErrorMessage(@StringRes resId: Int) {
            textInputLayout2.error = getString(resId)
            editText2.error = getString(resId)
        }

        RxTextView.textChangeEvents(editText2).
                skip(1).
                map { LoginId.validation(it.text().toString()) }.
                distinctUntilChanged().
                subscribeBy(
                        onNext = {
                            when (it) {
                                is LoginId.Companion.ValidateResult.ErrorEmpty -> showErrorMessage(R.string.error_message_is_empty)
                                is LoginId.Companion.ValidateResult.ErrorInputInvalidChar -> showErrorMessage(R.string.error_message_invalid_char)
                                is LoginId.Companion.ValidateResult.Correct -> clearErrorMessage()
                            }
                        }
                ).addTo(compositeDisposable)
    }

    private fun observeExEditText() {

        fun clearErrorMessage() {
            textInputLayout3.error = null
            editText3.setError(null, null)
        }

        fun showErrorMessage(@StringRes resId: Int) {
            textInputLayout3.error = getString(resId)
            editText3.setError(ExEditText.ONLY_ERROR_ICON, null)
        }

        editText3.toObservable().
                skip(1).
                map { LoginId.validation(it.text().toString()) }.
                distinctUntilChanged().
                subscribeBy(
                        onNext = {
                            when (it) {
                                is LoginId.Companion.ValidateResult.ErrorEmpty -> showErrorMessage(R.string.error_message_is_empty)
                                is LoginId.Companion.ValidateResult.ErrorInputInvalidChar -> showErrorMessage(R.string.error_message_invalid_char)
                                is LoginId.Companion.ValidateResult.Correct -> clearErrorMessage()
                            }
                        }
                ).addTo(compositeDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
