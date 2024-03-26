#include <iostream>
#include <vector>
#include "omp.h"

using namespace std;

vector<vector<int>> getFilledMatrix(int rows, int cols){
    vector<vector<int>> matrix(rows, vector<int>(cols, 0));

    int count = 1;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            matrix[i][j] = count++;
        }
    }
    matrix[rows / 2][cols / 2] = -100;

    return matrix;
}

long long calcSumOfMatrix(const vector<vector<int>>& matrix, int num_threads) {
    long long sum = 0;

    double t1 = omp_get_wtime();
#pragma omp parallel for reduction(+:sum) num_threads(num_threads)
    for(size_t i = 0; i < matrix.size(); ++i) {
        for (size_t j = 0; j < matrix[i].size(); ++j) {
            sum += matrix[i][j];
        }
    }
    double t2 = omp_get_wtime();

    cout << "\nTotal [Sum] time: " + to_string((t2 - t1)) <<  endl;

    return sum;
}

long calcSumOfRow(const vector<vector<int>>& matrix, int row) {
    long sum = 0;
    for(int i : matrix[row]) {
        sum += i;
    }
    return sum;
}

int getIndexOfRowWithMinSum(const vector<vector<int>>& matrix, int num_threads) {
    int index;
    long sum = calcSumOfRow(matrix, 0);

    double t1 = omp_get_wtime();
#pragma omp parallel for num_threads(num_threads)
    for(int i = 1; i < matrix.size(); i++){
        long tmpSum = calcSumOfRow(matrix, i);

#pragma omp critical
        {
            if(sum > tmpSum){
                if(sum > tmpSum){
                    sum = tmpSum;
                    index = i;
                }
            }
        }
    }
    double t2 = omp_get_wtime();

    cout << "\nTotal [Index] time: " + to_string((t2 - t1)) << endl;

    return index;
}

void printMatrix(const vector<vector<int>>& matrix) {
    for(const vector<int>& row : matrix) {
        for (int item : row) {
            cout << item << "\t";
        }
        cout << endl;
    }
}

int main() {
    vector<vector<int>> matrix = getFilledMatrix(5, 5);
    printMatrix(matrix);

    omp_set_nested(1);

    int index = 0;
    long long sum = 0;

    #pragma omp parallel sections
    {
        #pragma omp section
        {
            sum = calcSumOfMatrix(matrix, 100);
        }

        #pragma omp section
        {
            index = getIndexOfRowWithMinSum(matrix, 100);
        }
    }
    cout << "Sum : "  + to_string(sum) << endl;
    cout << "Index : " + to_string(index) + " sum = " + to_string(calcSumOfRow(matrix, index)) << endl;

    return 0;
}