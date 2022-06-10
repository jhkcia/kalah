import axios, { AxiosInstance } from "axios";
import { IFullBoard } from "../Board/IFullBoard";
import { IBoard } from "../BoardTable/IBoard";

class BoardApi {
    private static instance: BoardApi;
    private axiosInstance: AxiosInstance;

    private constructor(
    ) {
        this.axiosInstance = axios.create();
        this.initializeAxios();
    }

    private initializeAxios() {
        this.axiosInstance.interceptors.request.use(function (config) {
            const username = localStorage.getItem('username');

            if (username && config && config.headers && config.headers.common) {
                config.headers["username"] = username;
            }

            return config;
        });
    }

    public static getInstance(): BoardApi {
        if (!BoardApi.instance) {
            BoardApi.instance = new BoardApi();
        }

        return BoardApi.instance;
    }

    public getUserBoards() {
        return this.axiosInstance.get<IBoard[]>(`/api/board/self`);
    }

    public getAvailableBoards() {
        return this.axiosInstance.get<IBoard[]>(`/api/board/available`);
    }

    public addBoard() {
        return this.axiosInstance.post<IFullBoard>('/api/board');
    }

    public joinBoard(boardId: number) {
        return this.axiosInstance.post<IFullBoard>(`/api/board/${boardId}/join`);
    }

    public sowSeeds(boardId: number, pitIndex: number) {
        return this.axiosInstance.post<IFullBoard>(`/api/board/${boardId}/sowSeeds`, {
            pitIndex: pitIndex
        });
    }

    public get(boardId: number) {
        return this.axiosInstance.get<IFullBoard>(`/api/board/${boardId}`);
    }

};

export default BoardApi;