package com.example.inspectionreport.model

enum class Screen(internal val route: String) {
    REPORT_LIST("screenReportList"),
    REPORT_CREATE("screenCreateReport/{report}")
}