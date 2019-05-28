package akjaw.Repository

import akjaw.Model.Project

interface JsonRepository {
    fun getProjects(): List<Project>
}
