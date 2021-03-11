package ie.toxodev.retailinmotiontask.supportClasses.forecastResponse


import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "tram")
data class Tram(
    @Attribute(name = "destination")
    var destination: String? = null,
    @Attribute(name = "dueMins")
    var dueMins: String? = null
)