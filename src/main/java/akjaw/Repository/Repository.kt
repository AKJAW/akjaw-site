package akjaw.Repository

import model.Project

interface Repository {
    val projects: List<Project>
}
