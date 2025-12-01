package kr.ac.kopo.dorandoran.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.ac.kopo.dorandoran.dao.AlertDao;
import kr.ac.kopo.dorandoran.model.Alert;
import kr.ac.kopo.dorandoran.AlertBroadcaster;

@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    AlertDao dao;

    @Autowired
    private AlertBroadcaster alertBroadcaster; // ✅ broadcaster 주입

    @Override
    public List<Alert> list() {
        return dao.list();
    }

    @CacheEvict(value = "alerts", key = "#memberId")
    @Override
    public void contractCompleteadd(String memberId) {
        Alert alert = new Alert();
        alert.setMemberId(memberId);
        alert.setAlertType("계약");
        alert.setAlertMessage("계약이 수락되었습니다.");
        alert.setAlertRead(false);
        alert.setAlertTimestamp(now());

        dao.contractCompleteadd(alert);
        alertBroadcaster.sendToUser(memberId, alert); // ✅ 메시지 전송 위임
    }

    @CacheEvict(value = "alerts", key = "#memberId")
    @Override
    public void contractAdd(String memberId) {
        Alert alert = new Alert();
        alert.setMemberId(memberId);
        alert.setAlertType("계약");
        alert.setAlertMessage("신청된 계약이 있습니다.");
        alert.setAlertRead(false);
        alert.setAlertTimestamp(now());

        dao.contractAdd(alert);
        alertBroadcaster.sendToUser(memberId, alert); // ✅ 메시지 전송 위임
    }

    @Cacheable(value = "alerts", key = "#username")
    @Override
    public List<Alert> item(String username) {
        return dao.item(username);
    }

    @CacheEvict(value = "alerts", key = "#alertId")
    @Override
    public void delete(Long alertId) {
        dao.delete(alertId);
    }

    @CacheEvict(value = "alerts", key = "#memberId")
    @Override
    public void alertread(String memberId) {
        dao.alertread(memberId);
    }

    @CacheEvict(value = "alerts", key = "#memberId")
    @Transactional
    @Override
    public void chatalert(String memberId, String hostId, Long chatroomId) {
        System.out.println("⚙️ chatalert 실행 - memberId: " + memberId);

//        if (dao.existsChatAlert(memberId)) {
//            System.out.println("⚠️ 이미 존재하는 채팅 알림, 발송 안 함");
//            return;
//        }

        Alert alert = new Alert();
        alert.setMemberId(memberId);
        alert.setAlertType("채팅");
        alert.setAlertMessage(hostId + "님이 보낸 메시지가 있습니다.");
        alert.setAlertRead(false);
        alert.setAlertChatroomid(chatroomId);
        alert.setAlertTimestamp(now());

        dao.chatalert(alert);
        alertBroadcaster.sendToUser(memberId, alert); // ✅ 메시지 전송 위임
    }

    private String now() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
