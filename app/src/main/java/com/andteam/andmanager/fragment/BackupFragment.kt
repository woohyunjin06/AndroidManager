package com.andteam.andmanager.fragment

import android.Manifest
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.andteam.andmanager.R
import com.andteam.andmanager.adapter.BaseAdapter
import com.andteam.andmanager.adapter.RecyclerItem
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import com.andteam.andmanager.util.OnItemClickListener
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_backup.*
import kotlinx.android.synthetic.main.fragment_backup.view.*
import org.jetbrains.anko.support.v4.act

/**
* Created by hyunjin on 2018. 5. 16..
*/

class BackupFragment : Fragment(), OnItemClickListener{

    override fun onItemClick(position: Int) {
        //val pkg : String = mItems[position].packageNames

        TedPermission.with(activity)
                .setPermissionListener(mPermissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_FINE_LOCATION)
                .check()


    }

    private var adapter: RecyclerView.Adapter<*>? = null

    private val mItems = ArrayList<RecyclerItem>()

    private var mPackageManager : PackageManager? = null

    private var mRecyclerView : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val li : View = inflater.inflate(R.layout.fragment_backup, container, false)

        mRecyclerView = li.mRecyclerView
        val mLayoutManager = LinearLayoutManager(activity)
        mRecyclerView!!.layoutManager = mLayoutManager
        mPackageManager = activity!!.packageManager

        initRecyclerView()
        getApplicationList()



        return li
    }
    private fun getApplicationList(){
        doAsync {
            val mApplicationList : List<ApplicationInfo> = mPackageManager!!.getInstalledApplications(PackageManager.GET_META_DATA)
            for (info in mApplicationList) {
                try {
                    if (null != mPackageManager!!.getLaunchIntentForPackage(info.packageName)) {
                        mItems.add(RecyclerItem(info.loadLabel(mPackageManager).toString(), info.loadIcon(mPackageManager), info.name))
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
        mRecyclerView!!.setHasFixedSize(false)

        adapter = BaseAdapter(mItems, this)
        mRecyclerView!!.adapter = adapter
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)
    }

    private var mPermissionListener: PermissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            Toast.makeText(act, "Permission Granted", Toast.LENGTH_SHORT).show()
        }

        override fun onPermissionDenied(deniedPermissions: ArrayList<String>) {
            Toast.makeText(activity, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show()
        }


    }
}