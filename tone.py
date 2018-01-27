class Tone:
    """A class that does stuff."""
    tone_name = ""
    tone_id = ""
    score = 0.0

    def __init__(self, tone_obj):
        self.tone_name = tone_obj["tone_name"]
        self.tone_id = tone_obj["tone_id"]
        self.score = float(tone_obj["score"])
