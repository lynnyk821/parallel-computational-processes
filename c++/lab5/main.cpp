#include <iostream>
#include <vector>
#include <random>
#include "omp.h"

using namespace std;

vector<vector<int>> getFilledMatrix(int rows, int cols){
    vector<vector<int>> matrix(rows, vector<int>(cols, 0));

    random_device rd;
    mt19937 mersenne(rd());
    uniform_int_distribution<int> distribution(-10000, 10000);
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            matrix[i][j] = distribution(mersenne);
        }
    }

    return matrix;
}

long long calcSumOfMatrix(const vector<vector<int>>& matrix, int num_threads) {
    long long sum = 0;

    double t1 = omp_get_wtime();
#pragma omp parallel for reduction(+:sum) num_threads(num_threads)
    for(size_t i = 0; i < matrix.size(); ++i) {
        for(size_t j = 0; j < matrix[i].size(); ++j) {
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
    int index = 0;
    long sum = calcSumOfRow(matrix, 0);

    double t1 = omp_get_wtime();
#pragma omp parallel for num_threads(num_threads)
    for(int i = 1; i < matrix.size(); i++){
        long tmpSum = calcSumOfRow(matrix, i);
        if(sum > tmpSum) {
#pragma omp critical
        {
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
    vector<vector<int>> matrix = getFilledMatrix(10000, 10000);

    //omp_set_nested(1);

    int index = 0;
    long long sum = 0;

    #pragma omp parallel sections
    {
        #pragma omp section
        {
            sum = calcSumOfMatrix(matrix, 2);
        }

        #pragma omp section
        {
            index = getIndexOfRowWithMinSum(matrix, 2);
        }
    }

    cout << "Sum : "  + to_string(sum) << endl;
    cout << "Index : " + to_string(index) + " sum = " + to_string(calcSumOfRow(matrix, index)) << endl;

    return 0;
}