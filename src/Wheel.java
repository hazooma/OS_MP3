import java.util.ArrayList;

public class Wheel extends Thread {
	int capacity = 5;
	int count_of_currently_on_board_players;
	ArrayList<Player> list_of_currently_on_board_player;
	int maximum_waiting_time ;
	public Wheel(int x) {
		this.maximum_waiting_time=x;
	}
	
	

	public int getCount_of_currently_on_board_players() {
		return count_of_currently_on_board_players;
	}

	public void setCount_of_currently_on_board_players(int count_of_currently_on_board_players) {
		this.count_of_currently_on_board_players = count_of_currently_on_board_players;
	}

	public ArrayList<Player> getList_of_currently_on_board_player() {
		return list_of_currently_on_board_player;
	}

	public void setList_of_currently_on_board_player(ArrayList<Player> list_of_currently_on_board_player) {
		this.list_of_currently_on_board_player = list_of_currently_on_board_player;
	}

	public int getMaximum_waiting_time() {
		return maximum_waiting_time;
	}
	

	public void setMaximum_waiting_time(int maximum_waiting_time) {
		this.maximum_waiting_time = maximum_waiting_time;
	}

	public void load_players(Player p) {
		
		list_of_currently_on_board_player.add(p);
		if(IsFull() && this.getState() ==Thread.State.TIMED_WAITING ) {
			this.interrupt();
		}
	}
	
	public void run_ride()  {
		for(Player p :this.list_of_currently_on_board_player) {
			p.setRide_complete(false);
			p.setOn_board(true);
			this.setCount_of_currently_on_board_players(this.getCount_of_currently_on_board_players()+1);

		}
		

	}
	
	public void end_ride()  {
		count_of_currently_on_board_players=0;
		for(Player p :this.list_of_currently_on_board_player) {
			p.setRide_complete(true);
			p.setOn_board(false);
		}
		list_of_currently_on_board_player.clear();
	}
	public boolean IsFull() {
		return capacity==count_of_currently_on_board_players;
	}
	
	@SuppressWarnings("static-access")
	public void run(){ 
		while(true) {
		try {
			this.sleep(maximum_waiting_time);
		} catch (InterruptedException e) {
			run_ride();
			end_ride();
			e.printStackTrace();
			continue;
		}
		boolean f = false;
		if(!IsFull())f=true;
		run_ride();
		end_ride();
		if(f)break;
		}
	}  
}
