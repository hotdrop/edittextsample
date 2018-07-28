package jp.hotdrop.edittextsample.type

data class LoginId (
        val id: String
) {
    companion object {
        private const val PATTERN = "^[a-zA-Z0-9]+\$"
        sealed class ValidateResult {
            class Correct: ValidateResult()
            class ErrorEmpty: ValidateResult()
            class ErrorInputInvalidChar: ValidateResult()
        }
        fun validation(value: String): ValidateResult =
                when {
                    value.isEmpty() -> ValidateResult.ErrorEmpty()
                    !value.matches(PATTERN.toRegex()) -> ValidateResult.ErrorInputInvalidChar()
                    else -> ValidateResult.Correct()
                }
    }
}