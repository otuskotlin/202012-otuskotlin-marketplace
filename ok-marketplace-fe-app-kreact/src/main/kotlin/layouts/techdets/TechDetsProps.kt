package layouts.techdets

import models.TechDetModel
import react.RProps

data class TechDetsProps(
    var techDets: MutableSet<TechDetModel> = mutableSetOf()
): RProps {

}
