package ie.toxodev.retailinmotiontask.supportClasses.forecastResponse


import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "stopInfo")
data class ForecastResponse(
    @Attribute
    var created: String = "",
    @Attribute
    var stop: String = "",
    @Attribute
    var stopAbv: String = "",

    @PropertyElement(name="message")
    var message: String? = null,

    @Element
    var direction: List<Direction> = emptyList(),
)