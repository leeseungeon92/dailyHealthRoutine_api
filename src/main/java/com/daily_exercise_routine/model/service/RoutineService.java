package com.daily_exercise_routine.model.service;

import com.daily_exercise_routine.common.RoutinePart;
import com.daily_exercise_routine.model.dto.RoutineResponse;
import com.daily_exercise_routine.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineService {

    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;
    private final UserRoutineHistoryRepository userRoutineHistoryRepository;

    List<RoutinePart> parts = new ArrayList<>(List.of(
            RoutinePart.ARM, RoutinePart.AEROBIC, RoutinePart.BACK, RoutinePart.CHEST,
            RoutinePart.LOWER, RoutinePart.SHOULDER));

    public RoutineResponse getRoutine(String username) {

        userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);

        //(이번주 히스토리 조회)
        List<UserRoutineHistory> history =
                userRoutineHistoryRepository.findByUsernameAndWeekStartDate(username, weekStart);

        //(오늘 이미 추천된 적 있으면, 그 부위 재사용)
        Optional<UserRoutineHistory> todayHistoryOpt = history.stream()
                .filter(h -> h.getDate().equals(today))
                .findFirst();

        String part;
        boolean completed = false;

        if (todayHistoryOpt.isPresent()) {
            part = todayHistoryOpt.get().getPart();
            completed = todayHistoryOpt.get().getCompleted();
        } else {
            part = String.valueOf(noPartThisWeek(history));

            // 오늘 기록 저장
            UserRoutineHistory h = new UserRoutineHistory();
            h.setUsername(username);
            h.setDate(today);
            h.setWeekStartDate(weekStart);
            h.setPart(part);
            h.setCompleted(false);

            userRoutineHistoryRepository.save(h);
        }

        //해당 부위 루틴을 랜덤으로 가져오기
        List<RoutineEntity> partList = routineRepository.findByPart(part);
        Collections.shuffle(partList);

        List<RoutineResponse.Exercise> exercise = partList.stream()
                .map(e -> {
                    RoutineResponse.Exercise dto = new RoutineResponse.Exercise();
                    dto.setName(e.getName());
                    dto.setPart(e.getPart());
                    dto.setSets(e.getSets());
                    dto.setRaps(e.getRaps());
                    dto.setMemo(e.getMemo());
                    dto.setImage(e.getImage());
                    return dto;
                })
                .toList();

        RoutineResponse res = new RoutineResponse();
        res.setDate(today.toString());
        res.setExercises(exercise);
        res.setCompleted(completed);
        return res;

    }

    /**
     * 이번 주에 사용된 부위를 기준으로,
     * 아직 안 나온 부위 중 하나를 랜덤으로 선택.
     * 만약 6개를 다 쓴 상태라면 → 새 사이클 시작: 전체 중 랜덤.
     */
    private RoutinePart noPartThisWeek(List<UserRoutineHistory> history) {
        //이번 주에 이미 사용한 부위
        Set<String> usedPart = history.stream()
                .map(UserRoutineHistory::getPart)
                .collect(Collectors.toSet());

        //아직 안 쓴 부위
        List<RoutinePart> remainPart = new ArrayList<>(parts.stream()
                .filter(p -> !usedPart.contains(p))
                .toList());

        if (!remainPart.isEmpty()) {
            Collections.shuffle(remainPart);
            return remainPart.get(0);
        }

        // 6개를 다 썼으면 새 사이클 시작: 다시 전체에서 랜덤
        List<RoutinePart> newPart = new ArrayList<>(parts);
        Collections.shuffle(newPart);
        return newPart.get(0);
    }

    public void completeExercise(String username) {
        UserRoutineHistory todayExercise = userRoutineHistoryRepository.findByUsernameAndDate(username, LocalDate.now())
                .orElseThrow(() -> new IllegalArgumentException("오늘의 운동 루틴이 존재하지 않습니다."));

        todayExercise.setCompleted(true);
        userRoutineHistoryRepository.save(todayExercise);
    }
}
