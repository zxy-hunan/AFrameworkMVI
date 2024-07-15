package com.xy.mviframework.base.ui.vb.frg

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.FragmentBinding
import com.dylanc.viewbinding.base.FragmentBindingDelegate
import com.xy.mviframework.base.ui.vb.BaseAcy

/**
 * @file BaseFrg
 * @author zxy
 * @date 2024/7/15 11:29
 * @brief BaseFragment
 */
abstract class BaseFrg<VB : ViewBinding> : Fragment(), FragmentBinding<VB> by FragmentBindingDelegate() {
    abstract fun init(savedInstanceState: Bundle?)
    abstract fun initView()

    /**
     * 监听事件
     */
    abstract fun onListens()

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    /**
     * 数据是否加载过了
     */
    private var _isLoaded = false
    protected val isLoaded: Boolean
        get() = _isLoaded

    protected lateinit var mContext: Context
    protected lateinit var mActivity: BaseAcy<*>

    /**
     * @author zxy
     * @since 2024/7/15 11:37
     * @des 当Fragment与Activity关联时调用。
     * 这个方法在Fragment的生命周期中只调用一次
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mActivity = context as BaseAcy<*>
    }

    /**
     * @author zxy
     * @since 2024/7/15 11:37
     * @des 当Fragment被创建时调用。
     * 此处可以进行初始化工作，如读取保存的实例状态。
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * @author zxy
     * @since 2024/7/15 11:37
     * @des 创建Fragment的视图层次结构时调用。
     * 返回一个View对象，该对象代表Fragment的用户界面
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createViewWithBinding(inflater, container)
    }

    /**
     * @author zxy
     * @since 2024/7/15 11:37
     * @des 在onCreateView()返回后的视图已经创建完毕时调用。
     * 此时可以进行一些初始化操作，比如绑定视图。
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
        initView()
        onListens()
    }

    /**
     * @author zxy
     * @since 2024/7/15 11:38
     * @des 当Fragment变为活动状态时调用。
     * 此时Fragment的视图已经可见，但可能未聚焦。
     */
    override fun onStart() {
        super.onStart()
    }

    /**
     * @author zxy
     * @since 2024/7/15 11:38
     * @des 当Fragment变为活跃状态时调用。
     * 此时Fragment的视图已经可见并获得了焦点。
     */
    override fun onResume() {
        super.onResume()
        if (!isHidden || !_isLoaded) {
            lazyLoad()
            _isLoaded = true
        } else {
            if (_isLoaded) {
                unLazyLoad()
            }
        }
    }

    /**
     * @author zxy
     * @since 2024/7/15 11:38
     * @des 当Fragment失去焦点时调用。
     * 应该在此处保存任何临时状态。
     */
    override fun onPause() {
        super.onPause()
    }

    /**
     * @author zxy
     * @since 2024/7/15 11:38
     * @des 当Fragment不再可见时调用。
     * Fragment仍然附加在Activity上，但视图已隐藏。
     */
    override fun onStop() {
        super.onStop()
    }

    /**
     * @author zxy
     * @since 2024/7/15 11:39
     * @des 在onPause()或onDestroy()之前调用，用于保存Fragment的实例状态。
     * 从API 28开始，此方法在onStop()之后调用。
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    /**
     * @author zxy
     * @since 2024/7/15 11:39
     * @des 在Fragment的视图即将销毁时调用。
     * 此时应释放与视图相关的资源。
     */
    override fun onDestroyView() {
        super.onDestroyView()
    }

    /**
     * @author zxy
     * @since 2024/7/15 11:39
     * @des 在Fragment即将销毁时调用。
     * 应释放所有资源。
     */
    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * @author zxy
     * @since 2024/7/15 11:39
     * @des 当Fragment与Activity解除关联时调用。
     */
    override fun onDetach() {
        super.onDetach()
    }

    protected open fun unLazyLoad() {

    }

}