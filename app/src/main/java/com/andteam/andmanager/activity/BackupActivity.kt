package com.andteam.andmanager.activity

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.andteam.andmanager.R
import kotlinx.android.synthetic.main.activity_backup.*
import kotlinx.android.synthetic.main.appbar_main.*
import com.andteam.andmanager.adapter.RecyclerItem
import android.support.v7.widget.RecyclerView
import com.andteam.andmanager.adapter.BaseAdapter
import com.andteam.andmanager.util.OnItemClickListener
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread


class BackupActivity : AppCompatActivity(), OnItemClickListener{

    override fun onItemClick(position: Int) {
        val pkg : String = mItems[position].packageNames
        toast(pkg)
    }

    private var adapter: RecyclerView.Adapter<*>? = null

    private val mItems = ArrayList<RecyclerItem>()

    private var mPackageManager : PackageManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backup)

        setSupportActionBar(toolbar)
        val mLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLayoutManager

        mPackageManager = packageManager

        initRecyclerView()
        getApplicationList()
    }
    private fun getApplicationList(){
        doAsync {
            val mApplicationList : List<ApplicationInfo> = mPackageManager!!.getInstalledApplications(PackageManager.GET_META_DATA)
            var oneBlock : Int = mApplicationList.size
            for (info in mApplicationList) {
                try {
                    if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                        mItems.add(RecyclerItem(info.loadLabel(packageManager).toString(), info.loadIcon(packageManager), info.name))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            uiThread{
                it.adapter!!.notifyDataSetChanged()
            }
        }
    }
    private fun initRecyclerView() { // RecyclerView 기본세팅
        // 변경될 가능성 o : false 로 , 없다면 true.
        mRecyclerView.setHasFixedSize(false)

        // RecyclerView 에 Adapter 를 설정
        adapter = BaseAdapter(mItems, this)
        mRecyclerView.adapter = adapter

        // 지그재그 그리드 형식
        //mainBinding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        // 그리드 형식
        //mainBinding.recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        // 가로 또는 세로 스크롤 목록 형식
        mRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
