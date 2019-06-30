package Repository

import akjaw.Repository.JsonRepository
import akjaw.Repository.Repository
import com.beust.klaxon.KlaxonException
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.io.FileNotFoundException
import java.lang.Exception

class JsonRepositoryTest{

    @Test
    fun `if the file parse doesnt succeed it rethrows the error`(){
        try {
            JsonRepository("doesnt_exist.txt")
        } catch (e: Exception){
            Truth.assertThat(e is FileNotFoundException).isTrue()
        }

        try {
            JsonRepository("src/test/resources/json/not_json.txt")
        } catch (e: Exception){
            Truth.assertThat(e is KlaxonException).isTrue()
        }
    }

    @Test
    fun `after correct parse the project property is initialized`(){
        val repository = JsonRepository("src/test/resources/json/test_projects.json")
        Truth.assertThat(repository.projects).hasSize(2)
        Truth.assertThat(repository.projects[0].name).isEqualTo("name1")
        Truth.assertThat(repository.projects[1].name).isEqualTo("name2")
    }
}