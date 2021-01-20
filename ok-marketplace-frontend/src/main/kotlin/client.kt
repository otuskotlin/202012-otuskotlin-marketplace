import app.page.AppPageProps
import app.page.appPage
import kotlinx.browser.document
import kotlinx.browser.window
import react.dom.render

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            appPage(AppPageProps(

            ))
        }
    }
}

