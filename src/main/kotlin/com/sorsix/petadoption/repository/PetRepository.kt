package com.sorsix.petadoption.repository

import com.sorsix.petadoption.domain.Age
import com.sorsix.petadoption.domain.Pet
import com.sorsix.petadoption.domain.Sex
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PetRepository : JpaRepository<Pet, Long> {
    fun findAllByOrderByTimestampDesc(p: Pageable): Page<Pet>
    fun findAllByTypeNotIn(type: List<String>, p: Pageable): Page<Pet>

    @Query("SELECT p FROM Pet p WHERE (:name is null or p.name = :name) and (:type is null"
            + " or p.type = :type) and (:breed is null or p.breed = :breed) and (:age is null or p.age = :age)"
            + "and (:sex is null or p.sex = :sex) and (:color is null or p.color = :color)")
    fun findByFilters(@Param("name") name: String?, @Param("type") type: String?, @Param("breed") breed: String?,
                      @Param("age") age: Age?, @Param("sex") sex: Sex?, @Param("color") color: String?
                      , p: Pageable): Page<Pet>
}