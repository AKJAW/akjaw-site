package akjaw

import akjaw.HTML.SiteBuilder
import akjaw.Repository.JsonRepository

fun main(args: Array<String>){
    val projects = JsonRepository("data/projects.json").projects
    val siteBuilder = SiteBuilder(projects)
    val html = siteBuilder.build()
    val s = 's'
}