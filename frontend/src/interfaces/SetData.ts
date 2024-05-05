interface SetData {
    photo?: File | null;
    title: string;
    description: string;
    language: string;
    visibility: string | "public";
    fishqs: FishQData[];
}