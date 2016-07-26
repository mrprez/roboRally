package com.mrprez.roborally.server;

import java.util.ArrayList;

import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import com.mrprez.roborally.model.Robot;
import com.mrprez.roborally.model.RobotState;
import com.mrprez.roborally.model.history.Round;
import com.mrprez.roborally.model.history.Stage;
import com.mrprez.roborally.shared.RobotStateGwt;
import com.mrprez.roborally.shared.RoundGwt;
import com.mrprez.roborally.shared.StageGwt;

public class RoundCustomConverter extends DozerConverter<Round, RoundGwt> implements MapperAware {
	private Mapper mapper;
	
	
	public RoundCustomConverter() {
		super(Round.class, RoundGwt.class);
	}

	@Override
	public Round convertFrom(RoundGwt roundGwt, Round round) {
		throw new RuntimeException("Cannot convert from RoundGwt to Round");
	}

	@Override
	public RoundGwt convertTo(Round round, RoundGwt roundGwt) {
		if(roundGwt==null){
			roundGwt = new RoundGwt();
		}
		roundGwt.setNumber(round.getNumber());
		roundGwt.setRobotStateList(new ArrayList<RobotStateGwt>(round.getStateMap().size()));
		
		for(Robot robot : round.getStateMap().keySet()){
			roundGwt.getRobotStateList().add(convertTo(round.getStateMap().get(robot), robot.getNumber()));
		}
		
		roundGwt.setStageList(new ArrayList<StageGwt>());
		for(Stage stage : round.getStageList()){
			roundGwt.getStageList().add(mapper.map(stage, StageGwt.class));
		}
		
		return roundGwt;
	}
	
	private RobotStateGwt convertTo(RobotState robotState, int robotNb){
		RobotStateGwt robotStateGwt = mapper.map(robotState, RobotStateGwt.class);
		robotStateGwt.setRobotNb(robotNb);
		return robotStateGwt;
	}

	@Override
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	

}
