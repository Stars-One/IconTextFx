package com.starsone.icontext

import javafx.event.EventTarget
import javafx.scene.text.Font
import javafx.scene.text.Text
import tornadofx.*
import java.io.FileInputStream

/**
 *
 * @author StarsOne
 * @date Create in  2020/4/2 0002 19:44
 */
class IconText() : Text() {

    constructor(tffPath: String, unicode: Char) : this() {
        val myFont = Font.loadFont(javaClass.getResourceAsStream(tffPath),20.0)
        font = myFont
        text = unicode.toString()
    }

    constructor(tffFileInputStream: FileInputStream, unicode: Char) : this() {
        val myFont = Font.loadFont(tffFileInputStream,20.0)
        font = myFont
        text = unicode.toString()
    }

    /**
     * 设置字体大小为[size],可带单位,如px,em
     */
    fun setSize(size: String) {
        style = if (style.isNotBlank()) {
            if (style.contains("size")) {
                "-fx-font-size:$size;"
            } else {
                "$style-fx-font-size:$size;"
            }
        } else {
            "-fx-font-size:$size;"
        }
    }

    /**
     * 设置字体颜色为[color],color可以是16进制或者颜色名,如red
     */
    fun setColor(color: String) {
        style = if (style.isNotBlank()) {
            if (style.contains("fill")) {
                "-fx-fill:$color;"
            } else {
                "$style-fx-fill:$color;"
            }
        } else {
            "-fx-fill:$color;"
        }
    }
}

/**
 * 自定义字体图标
 * @param [tffPath] 字体文件路径
 * @param [unicode] 字体文件代码点
 * @param [color]可接受十六进制或字符串,例如red
 * @param [size]支持单位(pt,px,em,cm,ex)带单位 如1pt
 */
fun EventTarget.icontext(tffPath:String,unicode:Char, size: String="", color: String = "", op: (IconText.() -> Unit) = {}): IconText {

    val iconText = IconText(tffPath,unicode)
    if (size.isNotBlank()) {
        iconText.style {
            if (size.isNotBlank()) {
                when {
                    size.contains("pt") -> fontSize = size.substringBefore("pt").toInt().pt
                    size.contains("px") -> fontSize = size.substringBefore("px").toInt().px
                    size.contains("em") -> fontSize = size.substringBefore("em").toInt().em
                    size.contains("cm") -> fontSize = size.substringBefore("cm").toInt().cm
                    size.contains("ex") -> fontSize = size.substringBefore("ex").toInt().ex
                }
            }
            if (color.isNotBlank()) {
                fill = c(color)
            }
        }
    }
    return opcr(this, iconText, op)
}