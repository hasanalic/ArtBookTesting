package com.hasanali.artbooktesting.viewmodel

import com.hasanali.artbooktesting.repo.FakeArtRepository
import org.junit.Before

class ArtViewModelTest {

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup() {
        viewModel = ArtViewModel(FakeArtRepository())

    }

}