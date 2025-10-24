import org.junit.platform.suite.api.*;

@Suite
@SuiteDisplayName("LidCoin Test Suite")
@SelectPackages({
    "com.lidcoin.blockchain",
    "com.lidcoin.transaction",
    "com.lidcoin.wallet",
    "com.lidcoin.consensus",
    "com.lidcoin.kyc",
    "com.lidcoin.compliance.aml",
    "com.lidcoin.exchange",
    "com.lidcoin.security",
    "com.lidcoin.validator",
    "com.lidcoin.integration"
})
public class LidCoinTestSuite {
    // Test suite configuration
}
