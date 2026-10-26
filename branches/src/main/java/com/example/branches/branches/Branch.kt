package com.example.branches.branches

import androidx.compose.runtime.Stable
import com.example.domain.entities.remote.migration.NegoInfo

@Stable
data class Branch(
    val id: String,
    val name: String,
    val isOpen: Boolean
)


fun NegoInfo.toBranch() = Branch(
    id = sucursal.id,
    name = sucursal.name,
    isOpen = sucursal.isOpen
)


fun List<NegoInfo>.toBranchList() = map { it.toBranch() }
