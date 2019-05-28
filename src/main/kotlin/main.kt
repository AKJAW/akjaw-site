package akjaw

import akjaw.HTML.SiteBuilder
import akjaw.Repository.Repository

fun main(args: Array<String>){
    val projects = Repository("data/projects.json").getProjects()
    val siteBuilder = SiteBuilder(projects)
    val html = siteBuilder.build()
    val s = 's'
}