import com.example.newbondify.network.TruthOrDareQuestion
import retrofit2.Call
import retrofit2.http.GET

interface TruthOrDareApi {
    @GET("v1/truth")
    fun getTruthQuestion(): Call<TruthOrDareQuestion>

    @GET("api/dare")
    fun getDareQuestion(): Call<TruthOrDareQuestion>
}