package com.sethchhim.kuboo_client.ui.main.browser

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.sethchhim.kuboo_client.R
import com.sethchhim.kuboo_client.ui.main.browser.adapter.BrowserContentAdapter
import com.sethchhim.kuboo_client.ui.main.browser.adapter.BrowserPathAdapter
import com.sethchhim.kuboo_client.ui.main.browser.handler.PaginationHandler

open class BrowserBaseFragment : BrowserBaseFragmentImpl1_Content() {

    private var isFirstInstance = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentAdapter = BrowserContentAdapter(this, viewModel)
        contentSwipeRefreshLayout.setColorSchemeResources(R.color.lightColorAccent)
        paginationHandler = PaginationHandler(this, view)
        pathAdapter = BrowserPathAdapter(this, view, viewModel)
        pathRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        contentRecyclerView.adapter = contentAdapter
        pathRecyclerView.adapter = pathAdapter
    }

    override fun onPause() {
        super.onPause()
        mainActivity.disableSelectionMenuState()
    }

    override fun onResume() {
        super.onResume()
        if (!isFirstInstance) {
            contentAdapter.resetAllColorState()
            mainActivity.enableSelectionMenuState()
        }
        isFirstInstance = false
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setContentSpanCount(newConfig.orientation, contentRecyclerView.contentType)
    }

}