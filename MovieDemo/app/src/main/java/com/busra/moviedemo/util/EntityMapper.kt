package com.busra.moviedemo.util

/**
 * Entity can be persistence or network layer model.
 * ProjectModel is project level model.
 *
 * This class helps to separate model of domain and entity layer from project level models.
 * It is good practice for separating persistence models and network models.
 * Because they are not always the same. User mapper or converting them each other.
 *
 * https://buffer.com/resources/even-map-though-data-model-mapping-android-apps/
 *
 * */
interface EntityMapper<Entity, ProjectModel> {
    fun mapFromEntity(entityModel: Entity) : ProjectModel
    fun mapToEntity(domainModel: ProjectModel) : Entity
}