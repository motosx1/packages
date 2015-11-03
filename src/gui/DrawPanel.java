package gui;

import com.company.Car;
import com.company.City;
import com.company.Package;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {

    List<City> cities = new ArrayList<City>();
    List<Car> availableCars = new ArrayList<Car>();
    List<Car> carsOnTheRoute = new ArrayList<Car>();

    int carsAmount = 0;
    int startCityId = 0;
    int citySize = 20;

    int packageSize = 20;

    public void setCarsOnTheRoute(List<Car> carsOnTheRoute) {
        this.carsOnTheRoute = carsOnTheRoute;
    }

    public void setAvailableCars(List<Car> availableCars) {
        this.availableCars = availableCars;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public void setCarsAmount(int carsAmount) {
        this.carsAmount = carsAmount;
    }

    public void setStartCityId(int startCityId) {
        this.startCityId = startCityId;
    }


    public DrawPanel() {
        super();
        setLayout(getLayout());

        setPreferredSize(new Dimension(600, 600));
        setVisible(true);
    }

    public void run() {
        randomPoints();
    }

    private void randomPoints() {
        Dimension size = new Dimension(584, 561);   // TODO pokombinowac zeby to bylo automatycznie
        Insets insets = getInsets();

        int w = size.width - insets.left - insets.right - citySize;
        int h = size.height - insets.top - insets.bottom - citySize;

        double xC = w / 2;
        double yC = h / 2;
        double R = h / 2 - 50;
        double x;
        double y;
        for (City city : cities) {
            x = xC + R * Math.cos(2 * Math.PI * city.getId() / cities.size());
            y = yC + R * Math.sin(2 * Math.PI * city.getId() / cities.size());

            city.setPoint(new Point((int) x, (int) y));

        }
    }

    private void drawCities(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);

        for (City city : cities) {
            int x = (int) city.getPoint().getX();
            int y = (int) city.getPoint().getY();

            g2d.setColor(Color.red);
            g2d.fillRect(x, y, citySize, citySize);

            g2d.setColor(Color.red);
            g2d.drawString(city.getId() + " " + city.getName(), x - 5, y - 5);
        }


    }

    private void drawConnections(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);


        for (City city : cities) {
            int xStart = (int) city.getPoint().getX() + citySize / 2;
            int yStart = (int) city.getPoint().getY() + citySize / 2;

            List<City> associatedCitites = city.getAssociations();

            for (City associatedCity : associatedCitites) {
                int xEnd = (int) associatedCity.getPoint().getX() + citySize / 2;
                int yEnd = (int) associatedCity.getPoint().getY() + citySize / 2;

                g2d.setColor(Color.red);
                g2d.drawLine(xStart, yStart, xEnd, yEnd);

                int xStr = (xEnd - xStart) / 2 + xStart;
                int yStr = (yEnd - yStart) / 2 + yStart;
                g2d.setColor(Color.BLACK);


                g2d.drawString(Integer.toString(city.getAssociationWeight(associatedCity)), xStr - 10, yStr - 10);

            }

        }

    }


    public void updatePackages(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(SystemColor.BLUE);

        for (City city : cities) {

            Point p = city.getPoint();
            int x = (int) p.getX() + 30;
            int y = (int) p.getY() + 10;

            for (Package thePackage : city.getPackages()) {
                g2d.drawRect(x, y, packageSize, packageSize);
                g2d.drawString(Integer.toString(thePackage.getId()), x + 3, y - 2);

                if (thePackage.getCarId() != -1)
                    g2d.drawString(Integer.toString(thePackage.getCarId()), x + 3, y + 15);

                x += packageSize + 2;
            }
        }


    }


    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        drawCities(g);
        drawConnections(g);
        updatePackages(g);
    }
}
