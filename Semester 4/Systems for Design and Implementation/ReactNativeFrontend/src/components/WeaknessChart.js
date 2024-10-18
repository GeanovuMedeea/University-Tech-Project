import React, { useContext, useState, useEffect } from "react";
import { LineChart } from "react-native-chart-kit";
import { View, StyleSheet, Dimensions } from "react-native";
import { CharacterContext } from "../CharacterContext";

const screenWidth = Dimensions.get("window").width;

const WeaknessChart = () => {
  const { characters,setCharacters } = useContext(CharacterContext);
  const [chartData, setChartData] = useState({ labels: [], datasets: [{ data: [] }] });

  /*useEffect(() => {
    const fetchDataInterval = setInterval(() => {
      fetchCharacters();
    }, 5000);

    return () => clearInterval(fetchDataInterval);
  }, [fetchCharacters]);

  useEffect(() => {
    const newChartData = getChartData();
    setChartData(newChartData);
  }, [characters]);*/

 /* useEffect(() => {
    const fetchDataInterval = setInterval(() => {
      setCharacters(characters);
    }, 5000);

    return () => clearInterval(fetchDataInterval);
  }, [characters,setCharacters]);
*/

  useEffect(() => {
    const fetchDataInterval = setInterval(() => {
      const newChartData = getChartData();
      setChartData(newChartData);
    }, 100);
    return () => clearInterval(fetchDataInterval);
  }, [characters,setCharacters,setChartData,getChartData]);

  const getChartData = () => {
    const labels = ["Fire", "Frostbite", "Hemorrhage", "Holy", "Standard", "Poison", "Scarlet Rot", "Sleep"];
    const data = Array(labels.length).fill(0);

    characters.forEach(character => {
      labels.forEach((label, index) => {
        if (character.weakTo && character.weakTo.includes(label)) {
          data[index]++;
        }
      });
    });

    return {
      labels,
      datasets: [{ data }]
    };
  };

  return (
    <View testID="svg-group">
      <View testId="chartTitle" style={styles.chartTitle}>Weaknesses of all champions</View>
      <LineChart
        data={chartData}
        width={screenWidth}
        height={460}
        yAxisInterval={1}
        chartConfig={{
          propsForLabels: {
            fontSize: 13,
            fontFamily: "Arial",
            fontWeight: "Bold",
          },
          backgroundColor: "#e26a00",
          backgroundGradientFrom: "#d2b97b",
          backgroundGradientTo: "#e26a00",
          decimalPlaces: 2,
          color: (opacity = 1) => `rgba(235,208,149, ${opacity})`,
          labelColor: (opacity = 1) => `rgba(56,52,52, ${opacity})`,
          style: {
            borderRadius: 16,
          },
          propsForDots: {
            r: "6",
            strokeWidth: "2",
            stroke: "#ffffff"
          }
        }}
        bezier
        style={{
          marginVertical: 8,
          borderRadius: 16
        }}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  chartTitle: {
    alignItems: "center",
    fontFamily: "Arial",
    paddingTop: 10,
    color: "black",
    fontSize: 16,
    fontWeight: "Bold",
  },
});

export default WeaknessChart;
