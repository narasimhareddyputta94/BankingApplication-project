@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/account-statement")
    public ResponseEntity<AccountStatement> getAccountStatement(
            @RequestParam String accountNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        AccountStatement report = reportService.generateAccountStatement(accountNumber, startDate, endDate);
        return ResponseEntity.ok(report);
    }
}