package akjaw

class Attributes(vararg attributes: Pair<String, String>): Iterable<Attribute>{

    private val _attrs: MutableList<Attribute> = attributes.map {
        Attribute(it.first, it.second)
    }.toMutableList()

    val attrs: List<Attribute> = _attrs

    override fun iterator() = _attrs.iterator()

    fun add(attribute: Attribute){
        _attrs.add(attribute)
    }

    fun addAll(collection: Collection<Attribute>){
        _attrs.addAll(collection)
    }

    override fun toString(): String {
        return if(_attrs.isEmpty()){
            ""
        } else {
            _attrs.joinToString(" ", " ")
        }
    }

    operator fun get(index: Int) = attrs[index]
}