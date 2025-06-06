package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Slf4j
@Api(tags = "数据统计相关接口")
public class ReportController {
    @Autowired
    private ReportService reportService;
    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计")
    public Result<TurnoverReportVO> turnOverStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd")
                                                           LocalDate begin,
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                            LocalDate end) {
        log.info("营业额统计:{}{}", begin, end);
        return Result.success(reportService.getTurnoverStatistics(begin,end));
    }
    @GetMapping("/userStatistics")
    @ApiOperation("用户统计")
    public Result<UserReportVO> userStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd")
                                                   LocalDate begin,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                   LocalDate end) {
        log.info("用户统计", begin, end);
        return Result.success(reportService.getUserStatistics(begin,end));
    }
    @GetMapping("/ordersStatistics")
    @ApiOperation("订单统计")
    public Result<OrderReportVO> orderStatistics(@DateTimeFormat(pattern = "yyyy-MM-dd")
                                               LocalDate begin,
                                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                               LocalDate end) {
        log.info("订单统计:{}{}", begin, end);
        return Result.success(reportService.getOrderStatistics(begin,end));
    }
    @GetMapping("/top10")
    @ApiOperation("销量前10")
    public Result<SalesTop10ReportVO> top10S(@DateTimeFormat(pattern = "yyyy-MM-dd")
                                                 LocalDate begin,
                                                 @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                 LocalDate end) {
        log.info("销量排名:{}{}", begin, end);
        return Result.success(reportService.getTop10(begin,end));
    }
    @GetMapping("/export")
    @ApiOperation("导出报表")
    public void expot(HttpServletResponse response){
        reportService.getBusinessData(response);
    }



}
