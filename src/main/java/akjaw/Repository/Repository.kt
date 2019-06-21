package akjaw.Repository

import akjaw.Model.Project

interface Repository {
    fun getProjects(): List<Project>
}
