interface ViewData {
    title: string;
    fishQs: FishQData[];
    wasRated: boolean;
    rating: number | null;
    onRatingChange: (rating: number) => void;
}