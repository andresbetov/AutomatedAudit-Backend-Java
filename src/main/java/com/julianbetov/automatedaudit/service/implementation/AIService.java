package com.julianbetov.automatedaudit.service.implementation;

import com.julianbetov.automatedaudit.model.Registry;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIService {

    private final WebClient webClient;
    private final String apiKey;
    private final ResponseEntityExceptionHandler responseEntityExceptionHandler;

    public AIService(ResponseEntityExceptionHandler responseEntityExceptionHandler) {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("AI_API_KEY");
        this.webClient = WebClient.builder()
            .baseUrl("https://openrouter.ai/api/v1/")
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .build();
        this.responseEntityExceptionHandler = responseEntityExceptionHandler;
    }

    private String loadFile(String fileName, String format) throws IOException {
        return Files.readString(Paths.get("prompts/" + fileName + "." + format));
    }

    public String queryToAI(List<Registry> registries) throws IOException {
        String prompt = loadFile("analyze_data", "txt");
        prompt = prompt.replace("{{OUTPUT_JSON_STRUCTURE}}", loadFile("output_format", "json"));
        prompt = prompt.replace("{{REGISTRIES}}", registries.toString());

        Map<String, Object> body = new HashMap<>();
        body.put("model", "openai/gpt-oss-20b:free");

        Map<String, Object> message = Map.of(
            "role", "user",
            "content", prompt
        );

        body.put("messages", new Object[]{message});
        body.put("temperature", 0.3);
        body.put("max_tokens", 10000);

        Map<String, Object> response = webClient.post()
            .uri("/chat/completions")
            .body(Mono.just(body), Map.class)
            .retrieve()
            .bodyToMono(Map.class)
            .block();

        if (response == null || !response.containsKey("choices")) {
            throw new IllegalStateException("No response or choices from AI API");
        }

        List<?> choices = (List<?>) response.get("choices");
        if (choices.isEmpty()) {
            throw new IllegalStateException("Empty choices from AI API");
        }

        Map<?, ?> choice = (Map<?, ?>) choices.getFirst();
        Map<?, ?> messageResp = (Map<?, ?>) choice.get("message");
        if (messageResp == null || !messageResp.containsKey("content")) {
            throw new IllegalStateException("No content in AI API response");
        }

        return messageResp.get("content").toString();
    }


    public List<Registry> createRegistries() {
        List<Registry> registries = new ArrayList<>();
//        registries.add(Registry.builder().question("Is there a security policy approved by senior management that includes confidentiality, integrity, and availability of clinical and regulatory data?").answer("Yes").remarks("The policy was approved and disseminated; it includes confidentiality of sensitive data.").build());
//        registries.add(Registry.builder().question("Are former employee accounts immediately deleted, and are privilege reviews conducted periodically?").answer("No").remarks("Active accounts belonging to former employees and unnecessary administrator permissions were found.").build());
//        registries.add(Registry.builder().question("Is the principle of least privilege strictly enforced for access to systems and databases?").answer("No").remarks("Excessive access to clinical data by production staff was found.").build());
//        registries.add(Registry.builder().question("Does the system generate complete logs that identify the user, action, date, and time, and protect changes from tampering?").answer("No").remarks("The software does not record full traceability of changes to laboratory results.").build());
//        registries.add(Registry.builder().question("Are there automatic, off-site, encrypted backups with documented restoration procedures?").answer("No").remarks("There are no automatic or external backups; furthermore, they are not encrypted.").build());
//        registries.add(Registry.builder().question("Do database connections use mandatory TLS/SSL encryption in transit, and are backups/disks at rest protected?").answer("No").remarks("Connections are not TLS-compliant, and backups are legible in clear text.").build());
//        registries.add(Registry.builder().question("Is physical access to IT servers and laboratories controlled by appropriate security mechanisms?").answer("Yes").remarks("The document indicates physical access controls in laboratories and servers.").build());
//        registries.add(Registry.builder().question("Is there a documented and centralized procedure for reporting, logging, and tracking security incidents?").answer("Yes").remarks("There is an incident protocol managed by the Security Officer, but with deficiencies in implementation.").build());
//        registries.add(Registry.builder().question("Are the confidentiality and sensitive data protection regulations applicable to the healthcare sector (ISO 27001) complied with?").answer("No").remarks("Unauthorized access to clinical data by production staff was identified (breach of confidentiality).").build());
//        registries.add(Registry.builder().question("Does staff receive regular and documented training in information security?").answer("Yes").remarks("Training was provided, but there is no evidence of complete coverage of >90% of staff.").build());
//        registries.add(Registry.builder().question("Do contracts with critical suppliers include confidentiality and information security clauses?").answer("No").remarks("Contracts with suppliers are evident, but specific security clauses are missing.").build());
//        registries.add(Registry.builder().question("Is documentary evidence (minutes, backup copies, access logs) organized in an accessible repository?").answer("Yes").remarks("Documents were consolidated in a digital repository, although systematization is lacking.").build());
//        registries.add(Registry.builder().question("Is quality control software periodically validated, and is the process documented?").answer("No").remarks("Incomplete validation under real-world conditions was detected.").build());
//        registries.add(Registry.builder().question("Are security indicators defined, implemented, measured, and reported?").answer("No").remarks("Indicators were defined in the plan, but no ongoing monitoring was evident.").build());
//        registries.add(Registry.builder().question("Are there mechanisms in place to implement corrective actions based on the results of previous audits and ensure continuous improvement of the ISMS?").answer("Yes").remarks("Records of corrective actions and formal follow-up were evident.").build());


//        registries.add(Registry.builder().question("Is there a formal financial risk assessment process documented and reviewed annually?").answer("Yes").remarks("The process is outlined in the annual report and updated last quarter.").build());
//        registries.add(Registry.builder().question("Are all financial transactions reconciled on a monthly basis with supporting documentation?").answer("No").remarks("Some transactions from the previous quarter remain unreconciled due to staffing issues.").build());
//        registries.add(Registry.builder().question("Does the organization maintain segregated duties for financial approvals and disbursements?").answer("Yes").remarks("Duties are clearly separated as per the internal control policy.").build());
//        registries.add(Registry.builder().question("Are internal audits conducted at least quarterly with findings reported to management?").answer("No").remarks("Audits are only performed semi-annually, missing the quarterly requirement.").build());
//        registries.add(Registry.builder().question("Is there a whistleblower policy in place that protects employees reporting financial irregularities?").answer("Yes").remarks("The policy is included in the employee handbook and accessible online.").build());
//        registries.add(Registry.builder().question("Are financial statements prepared in accordance with GAAP standards?").answer("No").remarks("Deviations were noted in revenue recognition practices.").build());
//        registries.add(Registry.builder().question("Does the company have insurance coverage for financial fraud and errors?").answer("Yes").remarks("Coverage is active and renewed annually with adequate limits.").build());
//        registries.add(Registry.builder().question("Are vendor contracts reviewed for compliance before payments are processed?").answer("No").remarks("Several contracts were paid without full compliance checks.").build());
//        registries.add(Registry.builder().question("Is there a budget variance analysis performed and documented monthly?").answer("Yes").remarks("Analyses are conducted and discussed in management meetings.").build());
//        registries.add(Registry.builder().question("Are tax filings submitted on time and verified for accuracy?").answer("No").remarks("Late filings occurred due to incomplete records.").build());
//        registries.add(Registry.builder().question("Does the organization use encrypted channels for transmitting financial data?").answer("Yes").remarks("All transmissions are secured via HTTPS and VPN.").build());
//        registries.add(Registry.builder().question("Are employee expense reports audited randomly for compliance?").answer("No").remarks("No random audits were evidenced in the reviewed period.").build());
//        registries.add(Registry.builder().question("Is there a succession plan for key financial personnel?").answer("Yes").remarks("Plan is documented and includes training for backups.").build());
//        registries.add(Registry.builder().question("Are fixed assets inventoried and depreciated according to policy?").answer("No").remarks("Inventory discrepancies were found in the asset register.").build());
//        registries.add(Registry.builder().question("Does the audit committee meet regularly to oversee financial reporting?").answer("Yes").remarks("Meetings are held quarterly with minutes recorded.").build());

        registries.add(Registry.builder().question("Are all critical systems patched regularly?").answer("No").remarks("").build());
        registries.add(Registry.builder().question("Is there a documented disaster recovery plan for IT systems?").answer("Yes").remarks("Plan exists, updated last year.").build());
        registries.add(Registry.builder().question("Are all critical systems patched regularly?").answer("No").remarks("Some systems missed the latest patch cycle.").build());
        registries.add(Registry.builder().question("Is employee access to financial data restricted to job roles?").answer("Yes").remarks("").build());
        registries.add(Registry.builder().question("Does the organization comply with GDPR for data privacy?").answer("Yes").remarks("Compliance verified in last audit.").build());
        registries.add(Registry.builder().question("Are there regular fire drills for office safety?").answer("No").remarks("No drills conducted this year").build());
        registries.add(Registry.builder().question("Is multi-factor authentication enforced for all users?").answer("Maybe").remarks("MFA implemented for some systems, not all.").build());
        registries.add(Registry.builder().question("Are backups tested for restoration annually?").answer("Yes").remarks("Tests conducted, but results not fully documented").build());
        registries.add(Registry.builder().question("Is there a policy for remote work security?").answer("No").remarks("Policy in draft, not yet approved.").build());
        registries.add(Registry.builder().question("Are software licenses audited for compliance?").answer("").remarks("Audit planned but not yet started.").build());
        registries.add(Registry.builder().question("Is there a documented disaster recovery plan for IT systems?").answer("Yes").remarks("Duplicate entry;same plan referenced earlier.").build());
        registries.add(Registry.builder().question("Are physical security controls in place for server rooms?").answer("Yes").remarks("Keycard access implemented.").build());
        registries.add(Registry.builder().question("Is there an incident response team designated?").answer("No").remarks("Team not formally established.").build());
        registries.add(Registry.builder().question("Is there a formal process for vendor risk assessments?").answer("").remarks("").build());
        registries.add(Registry.builder().question("Are employees trained on phishing awareness?").answer("Yes").remarks("Training conducted, but attendance incomplete.").build());
        registries.add(Registry.builder().question("Is there a process for data retention and deletion?").answer("Unclear").remarks("Policy exists but lacks clarity on execution.").build());
        registries.add(Registry.builder().question("Are third-party vendors audited for security?").answer("No").remarks("No audits conducted in past 12 months.").build());
        registries.add(Registry.builder().question("Is encryption used for sensitive data at rest?").answer("Yes").remarks("AES-256 encryption in use.").build());
        registries.add(Registry.builder().question("Are there regular fire drills for office safety?").answer("No").remarks("Duplicate entry;no drills this year.").build());
        registries.add(Registry.builder().question("Is there a change management process for IT systems?").answer("").remarks("Process exists but not consistently followed.").build());
        registries.add(Registry.builder().question("Are access logs reviewed monthly?").answer("No").remarks("Logs are generated but not reviewed.").build());
        registries.add(Registry.builder().question("Is there a formal cybersecurity insurance policy?").answer("Yes").remarks("Policy active, renewed annually.").build());
        registries.add(Registry.builder().question("Are penetration tests conducted regularly?").answer("No").remarks("Last test was 18 months ago.").build());
        registries.add(Registry.builder().question("Is there a policy for BYOD (Bring Your Own Device)?").answer("Yes").remarks("Policy exists but enforcement is weak.").build());
        registries.add(Registry.builder().question("Are software licenses audited for compliance?").answer("No").remarks("Duplicate entry;audit still pending.").build());
        registries.add(Registry.builder().question("Is there a formal process for vendor risk assessments?").answer("Yes").remarks("Duplicate entry;process partially implemented.").build());
        registries.add(Registry.builder().question("Are there documented procedures for data breach response?").answer("").remarks("Procedures in draft, not finalized.").build());
        registries.add(Registry.builder().question("Is there a process to monitor insider threats?").answer("Uncertain").remarks("Some monitoring tools in place, but no formal process.").build());
        registries.add(Registry.builder().question("Are all endpoints protected with antivirus software?").answer("Yes").remarks("Antivirus deployed, but updates inconsistent.").build());
        registries.add(Registry.builder().question("Is there a policy for secure disposal of hardware?").answer("No").remarks("No formal disposal process documented.").build());

        return registries;
    }

}
