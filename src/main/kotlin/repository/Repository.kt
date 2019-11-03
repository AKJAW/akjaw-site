package repository

import model.Project

interface Repository {
    val projects: List<Project>
}
