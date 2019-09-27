package html

class Attributes(vararg attributes: Pair<String, String>): Iterable<Attribute>{
    private val _attrs: MutableList<Attribute> = attributes.map {
        Attribute(it.first, it.second)
    }.toMutableList()

    override fun iterator() = _attrs.iterator()

    operator fun get(index: Int): Attribute = _attrs[index]

    fun add(attribute: Attribute){
        _attrs.add(attribute)
    }

    fun addAll(attributes: Attributes){
        _attrs.addAll(attributes)
    }

    override fun toString(): String {
        return if(_attrs.isEmpty()){
            ""
        } else {
            _attrs.joinToString(" ", " ")
        }
    }

    fun replace(replacement: Attribute){
        if(_attrs.size == 0){
            _attrs.add(replacement)
            return
        }

        val index = _attrs.indexOfFirst {
            it.name == replacement.name
        }

        _attrs[index] = replacement
    }

    fun remove(name: String){
        val index = _attrs.indexOfFirst {
            it.name == name
        }

        _attrs.removeAt(index)
    }
}