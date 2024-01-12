package com.ariqhikari.githubuser.data.remote.response

import com.ariqhikari.githubuser.data.remote.response.GithubResponse
import com.ariqhikari.githubuser.data.remote.response.User
import org.junit.Assert.assertEquals
import org.junit.Test

class GithubResponseTest {

    @Test
    fun `github response should have correct total count`() {
        val githubResponse = GithubResponse(42, false, emptyList())
        assertEquals(42, githubResponse.totalCount)
    }

    @Test
    fun `github response should have correct incomplete results flag`() {
        val githubResponse = GithubResponse(0, true, emptyList())
        assertEquals(true, githubResponse.incompleteResults)
    }

    @Test
    fun `github response should have correct user list`() {
        val userList = listOf(
            User("gists_url", "repos_url", "following_url", "starred_url",
                "login", "followers_url", "type", "url", "subscriptions_url",
                42, "received_events_url", "avatar_url", "events_url",
                "html_url", false, 1, "gravatar_id", "node_id", "organizations_url")
        )
        val githubResponse = GithubResponse(0, false, userList)
        assertEquals(userList, githubResponse.items)
    }
}