package kz.islam.sanayatov.test.data

import lombok.EqualsAndHashCode

@EqualsAndHashCode
data class User(val name: String, val surname: String = "")