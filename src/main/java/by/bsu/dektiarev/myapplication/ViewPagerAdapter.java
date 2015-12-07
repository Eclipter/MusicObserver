package by.bsu.dektiarev.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by USER on 07.12.2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence titles[]; // This will Store the Titles of the Tabs which are Going to be passed when by.bsu.dektiarev.myapplication.ViewPagerAdapter is created
    int tabsCount; // Store the number of tabs, this will also be passed when the by.bsu.dektiarev.myapplication.ViewPagerAdapter is created

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mtabsCountumb) {
        super(fm);

        this.titles = mTitles;
        this.tabsCount = mtabsCountumb;

    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new AlbumsFragment();
            case 1:
                return new AlbumsFragment();
            case 2:
                return new AlbumsFragment();
            case 3:
                return new AlbumsFragment();
            default:
                break;
        }
        return null;
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return tabsCount;
    }
}
