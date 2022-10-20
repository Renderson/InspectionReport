package com.example.inspectionreport.navigation

enum class Screen(internal val route: String) {
    REPORT_LIST("screenReportList"),
    REPORT_CREATE("screenCreateReport/{report}")
}