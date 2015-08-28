package su.shev4enkostr.easycode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stas on 27.08.15.
 */
public class TeamFragment extends Fragment
{
    private RecyclerView recyclerView;
    private List<Team> team;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        initializeData();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_team, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_team);

        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llManager);

        RVTeamAdapter adapter = new RVTeamAdapter(team);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initializeData()
    {
        team = new ArrayList<>();
        team.add(new Team(getString(R.string.team_name_denis_layout), getString(R.string.team_about_denis_layout), R.drawable.denis_layout));
        team.add(new Team(getString(R.string.team_name_evgenij_php), getString(R.string.team_about_evgenij_php), R.drawable.evgenij_php));
        team.add(new Team(getString(R.string.team_name_artem_wordpress), getString(R.string.team_about_artem_wordpress), R.drawable.artem_wordpress));
        team.add(new Team(getString(R.string.team_name_sveta_marketing), getString(R.string.team_about_sveta_marketing), R.drawable.sveta_marketing));
        team.add(new Team(getString(R.string.team_name_julia_kopiraiter), getString(R.string.team_about_julia_kopiraiter), R.drawable.julia_kopiraiter));
        team.add(new Team(getString(R.string.team_name_mihail_social_networks), getString(R.string.team_about_mihail_social_networks), R.drawable.mihail_curator_social_networks));
        team.add(new Team(getString(R.string.team_name_andrey_photoshop), getString(R.string.team_about_andrey_photoshop), R.drawable.andrey_photoshop));
        team.add(new Team(getString(R.string.team_name_julia_english), getString(R.string.team_about_julia_english), R.drawable.julia_english));
    }
}

class RVTeamAdapter extends RecyclerView.Adapter<RVTeamAdapter.TeamViewHolder>
{
    public static class TeamViewHolder extends RecyclerView.ViewHolder
    {

        CardView cvTeam;
        TextView teamName;
        TextView teamAbout;
        ImageView teamPicture;

        TeamViewHolder(View itemView)
        {
            super(itemView);
            cvTeam = (CardView)itemView.findViewById(R.id.cv_team);
            teamName = (TextView)itemView.findViewById(R.id.team_name);
            teamAbout = (TextView)itemView.findViewById(R.id.team_about);
            teamPicture = (ImageView)itemView.findViewById(R.id.team_picture);
        }
    }

    List<Team> persons;

    RVTeamAdapter(List<Team> persons)
    {
        this.persons = persons;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment_team, viewGroup, false);
        TeamViewHolder pvh = new TeamViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(TeamViewHolder personViewHolder, int i)
    {
        personViewHolder.teamName.setText(persons.get(i).getName());
        personViewHolder.teamAbout.setText(persons.get(i).getAbout());
        personViewHolder.teamPicture.setImageResource(persons.get(i).getPictureId());
    }

    @Override
    public int getItemCount()
    {
        return persons.size();
    }
}

class Team
{
    private String name;
    private String about;
    private int pictureId;

    public Team(String name, String about, int pictureId)
    {
        this.name = name;
        this.about = about;
        this.pictureId = pictureId;
    }

    public String getName()
    {
        return name;
    }

    public String getAbout()
    {
        return about;
    }

    public int getPictureId()
    {
        return pictureId;
    }
}


