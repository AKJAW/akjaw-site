package akjaw.Repository

import akjaw.Model.Project

interface Repository {
    val projects: List<Project>
}
