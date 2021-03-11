package ie.toxodev.retailinmotiontask.supportClasses.forecastResponse


import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "direction")
data class Direction(
    @Attribute(name = "name")
    var name: String? = null,

    @Element
    var tram: List<Tram> = emptyList()
)