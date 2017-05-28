package com.explod.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class Lease(
        @SerializedName("lease_id") val leaseId: String,
        val name: String,
        val expiration: Date,
        val url: String
)