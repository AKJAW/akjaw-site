package html.special_tag

class ListTag: SpecialTag {
    override val signature: String = "list"

    override fun isSpecialTag(name: String): Boolean {
        return name.startsWith(name)
    }

    override fun createTag() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}