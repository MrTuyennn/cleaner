package com.inetkr.cleaner.data.local.scanner

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * PreferenceRepository - Lớp quản lý các cài đặt của ứng dụng thông qua SharedPreferences
 * 
 * Chức năng chính:
 * 1. Lưu trữ và truy xuất các cài đặt người dùng
 * 2. Quản lý danh sách blacklist và whitelist
 * 3. Cung cấp các cờ bật/tắt tính năng dọn dẹp
 * 4. Lưu trữ cài đặt giao diện và hành vi ứng dụng
 */
class PreferenceRepository(ctx: Context){
    private val prefs = PreferenceManager.getDefaultSharedPreferences(ctx)
    
    companion object {
        // Các khóa và giá trị mặc định cho SharedPreferences
        
        // Cài đặt tự động bảo vệ
        private const val autoWhite_key = "auto_white"
        private const val autoWhite_defVal = true
        
        // Danh sách đen (blacklist) - các file/thư mục cần xóa
        private const val blacklist_key = "blacklist"
        private var blacklist_defVal = Constants.blacklistDefault
        private const val blacklistOn_key = "blacklistOn"
        private var blacklistOn_defVal = Constants.blacklistOnDefault
        
        // Cài đặt dọn dẹp tự động khi khởi động
        private const val bootCleanup_key = "boot_cleanup"
        private const val bootCleanup_defVal = false
        
        // Cài đặt dọn dẹp file APK
        private const val cleanApk_key = "clean_apk"
        private const val cleanApk_defVal = true
        
        // Cài đặt dọn dẹp clipboard
        private const val cleanClipboard_key = "clean_clipboard"
        private const val cleanClipboard_defVal = false
        
        // Cài đặt dọn dẹp corpse files (thư mục ứng dụng đã gỡ)
        private const val cleanCorpse_key = "clean_corpse"
        private const val cleanCorpse_defVal = false
        
        // Cài đặt dọn dẹp file rỗng
        private const val cleanEmptyFile_key = "clean_empty_file"
        private const val cleanEmptyFile_defVal = true
        
        // Cài đặt dọn dẹp thư mục rỗng
        private const val cleanEmptyFolder_key = "clean_empty_folder"
        private const val cleanEmptyFolder_defVal = true
        
        // Cài đặt dọn dẹp định kỳ
        private const val cleanEvery_key = "clean_every"
        private const val cleanEvery_defVal = -1
        
        // Cài đặt dọn dẹp file chung
        private const val cleanGeneric_key = "clean_generic"
        private const val cleanGeneric_defVal = true
        
        // Cài đặt đóng ứng dụng chạy nền
        private const val closeBgApps_key = "close_bg_apps"
        private const val closeBgApps_defVal = true
        
        // Cài đặt màu động
        private const val dynamicColor_key = "dynamic_color"
        private const val dynamicColor_defVal = true
        
        // Cài đặt chạy nhiều lần
        private const val multiRun_key = "multi_run"
        private const val multiRun_defVal = 1
        
        // Cài đặt một click
        private const val oneClick_key = "one_click"
        private const val oneClick_defVal = false
        
        // Cài đặt màu đen tuyệt đối
        private const val pitchBlack_key = "pitch_black"
        private const val pitchBlack_defVal = false
        
        // Đếm số lần chạy
        private const val runCount_key = "run_count"
        private const val runCount_defVal = 0
        
        // Cài đặt theme
        private const val theme_key = "theme"
        private const val theme_defVal = "auto"
        
        // Danh sách trắng (whitelist) - các file/thư mục cần bảo vệ
        private const val whitelist_key = "whitelist"
        private var whitelist_defVal = Constants.whitelistDefault
        private const val whitelistOn_key = "whitelistOn"
        private var whitelistOn_defVal = Constants.whitelistOnDefault
    }
    
    // Các property getter/setter cho các cài đặt
    
    // Tự động bảo vệ thư mục có từ khóa đặc biệt
    var autoWhite: Boolean
        get() = prefs.getBoolean(autoWhite_key,autoWhite_defVal) ?: autoWhite_defVal
        set(v) = prefs.edit().putBoolean(autoWhite_key,v).apply()
    
    // Dọn dẹp tự động khi khởi động
    var bootCleanup: Boolean
        get() = prefs.getBoolean(bootCleanup_key,bootCleanup_defVal) ?: bootCleanup_defVal
        set(v) = prefs.edit().putBoolean(bootCleanup_key,v).apply()
    
    // Danh sách đen đầy đủ
    var blacklist: Set<String>
        get() = prefs.getStringSet(blacklist_key,blacklist_defVal) ?: blacklist_defVal
        set(v) = prefs.edit().putStringSet(blacklist_key,v).apply()
    
    // Danh sách đen được kích hoạt
    var blacklistOn: Set<String>
        get() = prefs.getStringSet(blacklistOn_key,blacklistOn_defVal) ?: blacklistOn_defVal
        set(v) = prefs.edit().putStringSet(blacklistOn_key,v).apply()
    
    // Dọn dẹp file APK
    var cleanApk: Boolean
        get() = prefs.getBoolean(cleanApk_key,cleanApk_defVal) ?: cleanApk_defVal
        set(v) = prefs.edit().putBoolean(cleanApk_key,v).apply()
    
    // Dọn dẹp clipboard
    var cleanClipboard: Boolean
        get() = prefs.getBoolean(cleanClipboard_key,cleanClipboard_defVal) ?: cleanClipboard_defVal
        set(v) = prefs.edit().putBoolean(cleanClipboard_key,v).apply()
    
    // Dọn dẹp corpse files (thư mục ứng dụng đã gỡ)
    var cleanCorpse: Boolean
        get() = prefs.getBoolean(cleanCorpse_key,cleanCorpse_defVal) ?: cleanCorpse_defVal
        set(v) = prefs.edit().putBoolean(cleanCorpse_key,v).apply()
    
    // Dọn dẹp file rỗng
    var cleanEmptyFile: Boolean
        get() = prefs.getBoolean(cleanEmptyFile_key,cleanEmptyFile_defVal) ?: cleanEmptyFile_defVal
        set(v) = prefs.edit().putBoolean(cleanEmptyFile_key,v).apply()
    
    // Dọn dẹp thư mục rỗng
    var cleanEmptyFolder: Boolean
        get() = prefs.getBoolean(cleanEmptyFolder_key,cleanEmptyFolder_defVal) ?: cleanEmptyFolder_defVal
        set(v) = prefs.edit().putBoolean(cleanEmptyFolder_key,v).apply()
    
    // Dọn dẹp định kỳ (số ngày)
    var cleanEvery: Int
        get() = prefs.getInt(cleanEvery_key,cleanEvery_defVal) ?: cleanEvery_defVal
        set(v) = prefs.edit().putInt(cleanEvery_key,v).apply()
    
    // Dọn dẹp file chung
    var cleanGeneric: Boolean
        get() = prefs.getBoolean(cleanGeneric_key,cleanGeneric_defVal) ?: cleanGeneric_defVal
        set(v) = prefs.edit().putBoolean(cleanGeneric_key,v).apply()
    
    // Đóng ứng dụng chạy nền
    var closeBgApps: Boolean
        get() = prefs.getBoolean(closeBgApps_key,closeBgApps_defVal) ?: closeBgApps_defVal
        set(v) = prefs.edit().putBoolean(closeBgApps_key,v).apply()
    
    // Màu động
    var dynamicColor: Boolean
        get() = prefs.getBoolean(dynamicColor_key,dynamicColor_defVal) ?: dynamicColor_defVal
        set(v) = prefs.edit().putBoolean(dynamicColor_key,v).apply()
    
    // Chạy nhiều lần
    var multiRun: Int
        get() = prefs.getInt(multiRun_key,multiRun_defVal) ?: multiRun_defVal
        set(v) = prefs.edit().putInt(multiRun_key,v).apply()
    
    // Một click
    var oneClick: Boolean
        get() = prefs.getBoolean(oneClick_key,oneClick_defVal) ?: oneClick_defVal
        set(v) = prefs.edit().putBoolean(oneClick_key,v).apply()
    
    // Màu đen tuyệt đối
    var pitchBlack: Boolean
        get() = prefs.getBoolean(pitchBlack_key,pitchBlack_defVal) ?: pitchBlack_defVal
        set(v) = prefs.edit().putBoolean(pitchBlack_key,v).apply()
    
    // Đếm số lần chạy
    var runCount: Int
        get() = prefs.getInt(runCount_key,runCount_defVal) ?: runCount_defVal
        set(v) = prefs.edit().putInt(runCount_key,v).apply()
    
    // Theme
    var theme: String
        get() = prefs.getString(theme_key,theme_defVal) ?: theme_defVal
        set(v) = prefs.edit().putString(theme_key,v).apply()
    
    // Danh sách trắng đầy đủ
    var whitelist: Set<String>
        get() = prefs.getStringSet(whitelist_key,whitelist_defVal) ?: whitelist_defVal
        set(v) = prefs.edit().putStringSet(whitelist_key,v).apply()
    
    // Danh sách trắng được kích hoạt
    var whitelistOn: Set<String>
        get() = prefs.getStringSet(whitelistOn_key,whitelistOn_defVal) ?: whitelistOn_defVal
        set(v) = prefs.edit().putStringSet(whitelistOn_key,v).apply()
}