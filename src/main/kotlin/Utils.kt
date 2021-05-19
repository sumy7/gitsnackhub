/**
 * 将字符串开始的数字替换成%s
 * @param str 输入字符串
 * @return 替换后的字符串
 */
fun getScoreTemplate(str: String?): String {
    if (str.isNullOrBlank()) {
        return "%s"
    }
    return str.replaceFirst(Regex("[0-9]+"), "%s")
}