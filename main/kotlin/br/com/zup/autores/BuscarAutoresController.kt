package br.com.zup.autores

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import javax.transaction.Transactional

@Controller("/autores")
class BuscarAutoresController (val autorRepository: AutorRepository){

    @Get
    fun buscar(@QueryValue(defaultValue = "") email: String): HttpResponse<Any>{
        if(email.isBlank()){
            val autores = autorRepository.findAll();

            val resposta = autores.map { autor -> DetalhesDoAutorResponse(autor) }

            return HttpResponse.ok(resposta)
        }

        val possivelAutor = autorRepository.buscaPorEmail(email)
        if(!possivelAutor.isPresent){
            return HttpResponse.notFound()
        }

        val autor = possivelAutor.get()
        return HttpResponse.ok(DetalhesDoAutorResponse(autor))
    }

}